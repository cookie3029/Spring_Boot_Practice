package com.kabigon.movie;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.TypedSort;
import org.springframework.test.annotation.Commit;

import com.kabigon.movie.model.Member;
import com.kabigon.movie.model.Movie;
import com.kabigon.movie.model.MovieImage;
import com.kabigon.movie.model.Review;
import com.kabigon.movie.repository.MemberRepository;
import com.kabigon.movie.repository.MovieImageRepository;
import com.kabigon.movie.repository.MovieRepository;
import com.kabigon.movie.repository.ReviewRepository;

@SpringBootTest
public class RepositoryTest {
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieImageRepository movieImageRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	// @Test
	@Commit
	@Transactional // 여러 개의 데이터를 삽입하므로 모두 성공하거나 실패하도록 하기 위해서 ㅜ가
	public void insertMovie () {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Movie movie = Movie.builder().title("Movie..." + i).build();
			movieRepository.save(movie);
			
			int count = (int)(Math.random() * 5) + 1;
			
			for(int j = 0; j < count; j++) {
				MovieImage movieImage = MovieImage.builder()
						.uuid(UUID.randomUUID().toString())
						.movie(movie)
						.imgName("test" + j + ".jpg")
						.build();
				movieImageRepository.save(movieImage);
			}
		});
	}
	
	// @Test
	@Commit
	@Transactional
	public void insertMember() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Member member = Member.builder()
					.email("tester" + i + "@test.com")
					.pw("1234")
					.nickname("잠만보" + i)
					.build();
			
			memberRepository.save(member);
		});
	}
	
	// @Test
	@Commit
	@Transactional
	public void insertReview() {
		IntStream.rangeClosed(1, 200).forEach(i -> {
			// 존재하는 영화번호
			Long mno = (long)(Math.random() * 100) + 1;
			
			// 회원 번호
			Long mid = (long)(Math.random() * 100) + 1;
			
			Movie movie = Movie.builder().mno(mno).build();
			Member member = Member.builder().mid(mid).build();
			
			Review movieReview = Review.builder().member(member).movie(movie)
					.grade((int)(Math.random() * 5) + 1)
					.text("영화 리뷰..." + i)
					.build();
			
			reviewRepository.save(movieReview);
		});
	}
	
	// @Test
	public void testListPage() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));
		Page<Object[]> result = movieRepository.getListPage(pageRequest);
		
		for (Object[] objects : result) {
			System.out.println(Arrays.toString(objects));
		}
	}
	
	// @Test
	public void testGetMovieWithAll() {
		List<Object[]> result = movieRepository.getMovieAll(92L);
		
		for (Object[] arr : result) {
			System.out.println(Arrays.toString(arr));			
		}
	}
	
	// @Test
	public void testGetMovieReviews() {
		Movie movie = Movie.builder().mno(98L).build();	
		List<Review> result = reviewRepository.findByMovie(movie);

		for (Review review : result) {
			System.out.println(review.getMember().getNickname());
		}
	}
	
	// @Test
	@Commit
	@Transactional
	public void testDeleteMember() {
		Long mid = 14L;
		Member member = Member.builder().mid(mid).build();
		
		reviewRepository.deleteByMember(member);
		memberRepository.deleteById(mid);
	}
}

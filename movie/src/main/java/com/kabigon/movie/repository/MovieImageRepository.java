package com.kabigon.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabigon.movie.model.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

}

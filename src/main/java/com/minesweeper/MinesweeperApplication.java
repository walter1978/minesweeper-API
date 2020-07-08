package com.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MinesweeperApplication {
	public static void main(String[] args) {
		SpringApplication.run(MinesweeperApplication.class, args);
	}
}


package com.puzzlertracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Puzzler Tracker
 * Configured for CAT question generation without database dependencies
 */
@SpringBootApplication
public class PuzzlerTrackerApplication {

    public static void main(String[] args) {
        System.out.println("🚀 Starting Puzzler Tracker - CAT Question Generator");
        System.out.println("📚 Ready to generate CAT exam questions using AI!");
        SpringApplication.run(PuzzlerTrackerApplication.class, args);
    }
}

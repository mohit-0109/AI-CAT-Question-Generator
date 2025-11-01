package com.puzzlertracker.controller;

import com.puzzlertracker.model.Puzzle;
import com.puzzlertracker.service.PuzzleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Puzzle controller - disabled by default for CAT question mode
 * Enable by setting: app.puzzle.service.enabled=true
 */
@RestController
@RequestMapping("/api/puzzles")
@CrossOrigin(origins = "*") // Allow all origins for simplicity in development
@ConditionalOnProperty(name = "app.puzzle.service.enabled", havingValue = "true", matchIfMissing = false)
public class PuzzleController {

    private final PuzzleService puzzleService;

    @Autowired
    public PuzzleController(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @GetMapping
    public List<Puzzle> getAllPuzzles() {
        return puzzleService.getAllPuzzles();
    }
}

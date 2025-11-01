package com.puzzlertracker.service;

import com.puzzlertracker.model.Puzzle;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

/**
 * Puzzle service - disabled by default for CAT question mode
 * Enable by setting: app.puzzle.service.enabled=true
 */
@Service
@ConditionalOnProperty(name = "app.puzzle.service.enabled", havingValue = "true", matchIfMissing = false)
public class PuzzleService {

    /**
     * Mock implementation - returns empty list when database is not available
     */
    public List<Puzzle> getAllPuzzles() {
        // Return empty list for now - this prevents frontend errors
        return new ArrayList<>();
    }
}

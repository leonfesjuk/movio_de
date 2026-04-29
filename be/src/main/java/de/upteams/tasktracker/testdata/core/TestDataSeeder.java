package de.upteams.tasktracker.testdata.core;

public interface TestDataSeeder {
    String name();
    SeedResult seed(SeedCommand command);
}

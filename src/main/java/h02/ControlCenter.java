package h02;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A class that controls the {@linkplain Robot robots} and their actions.
 */
public class ControlCenter {

    /**
     * Creates a new line of {@linkplain ScanRobot ScanRobots}.
     *
     * @return An array containing the newly initialised robots
     */
    public ScanRobot[] initScanRobots() {
        // assuming the width of the world not less than 1
        ScanRobot[] scanArray = new ScanRobot[World.getWidth() - 1];
        for (int i = 0; i < scanArray.length; i++) {
            scanArray[i] = new ScanRobot(i + 1, 0, Direction.UP, 0);
        }
        return scanArray;
    }

    /**
     * Creates a new line of {@linkplain CleanRobot CleanRobots}.
     *
     * @return An array containing the newly initialised robots
     */
    public CleanRobot[] initCleaningRobots() {
        // assuming the height of the world not less than 1
        CleanRobot[] cleanArray = new CleanRobot[World.getHeight() - 1];
        for (int i = 0; i < cleanArray.length; i++) {
            cleanArray[i] = new CleanRobot(0, i + 1, Direction.RIGHT, 0);
        }
        return cleanArray;
    }

    /**
     * Inverts the given array by swapping the first and last entry, continuing with the second and second last entry and so on until the entire array has been inverted.
     *
     * @param robots The array to invert
     */
    public void reverseRobots(Robot[] robots) {
        for (int i = 0; i < robots.length / 2; i++) {
            Robot temp;
            temp = robots[i];
            robots[i] = robots[robots.length - 1 - i];
            robots[robots.length - 1 - i] = temp;
        }
    }

    /**
     * Rotates the {@linkplain Robot robots} in the given array in ascending order and calls {@link #checkForDamage} on every {@link Robot} after its rotation.
     *
     * @param robots The array of {@linkplain Robot robots} to rotate
     */
    public void rotateRobots(Robot[] robots) {
        for (Robot robot : robots) {
            robot.turnLeft();
            robot.turnLeft();
            checkForDamage(robot);
        }
    }

    /**
     * Simulates inspecting a {@link Robot} for wear, turning it off if it should no longer serve. Currently implemented as a coin flip.
     *
     * @param robot The {@link Robot} to inspect
     */
    public void checkForDamage(Robot robot) {
        final double p = 0.5;
        if (Math.random() > p) {
            robot.turnOff();
        }
    }

    /**
     * Replaces the {@linkplain Robot robots} that are turned off in the provided array with new ones. <br>
     * The method expects either an array of {@linkplain ScanRobot ScanRobots} or {@linkplain CleanRobot CleanRobots} and uses the correct class when replacing the robots.
     *
     * @param robots An array possibly containing {@linkplain Robot robots} that are turned off and need to be replaced
     */
    public void replaceBrokenRobots(Robot[] robots) {
        if (isScanRobotArray(robots)) {
            for (int i = 0; i < robots.length; i++) {
                if (robots[i].isTurnedOff()){
                    robots[i] = new ScanRobot(robots[i].getX(), robots[i].getY(), robots[i].getDirection(), robots[i].getNumberOfCoins());
                }
            }
        } else {
            for (int i = 0; i < robots.length; i++) {
                if (robots[i].isTurnedOff()){
                    robots[i] = new CleanRobot(robots[i].getX(), robots[i].getY(), robots[i].getDirection(), robots[i].getNumberOfCoins());
                }
            }
        }
    }

    /**
     * Tests whether the given array is an array of {@linkplain ScanRobot ScanRobots} or not.
     *
     * @param robots The array to test
     * @return Whether the given array is an array of {@linkplain ScanRobot ScanRobots}
     */
    public boolean isScanRobotArray(Robot[] robots) {
        return robots instanceof ScanRobot[];
    }

    /**
     * Calls {@link #reverseRobots}, {@link #rotateRobots} and {@link #replaceBrokenRobots} in that order, with the given array as the argument
     *
     * @param robots The array to perform the aforementioned actions on
     */
    public void spinRobots(Robot[] robots) {
        reverseRobots(robots);
        rotateRobots(robots);
        replaceBrokenRobots(robots);
    }

    /**
     * Moves the robots to the end of the world, in ascending order and one at a time.
     *
     * @param robots The robots to move
     */
    public void returnRobots(Robot[] robots) {
        for (Robot robot : robots) {
            while (robot.isFrontClear()) {
                robot.move();
            }
        }
    }

    /**
     * Scans the world using the provided {@linkplain ScanRobot ScanRobots} and returns an array containing the scan results.
     *
     * @param scanRobots The robots to scan the world with
     * @return An array detailing which world fields contain at least one coin
     */
    public boolean[][] scanWorld(ScanRobot[] scanRobots) {
        boolean[][] coinPositions = new boolean[World.getHeight()][World.getWidth()];
        // initialize the array of boolean array
        for (int j = 0; j < World.getHeight(); j++) {
            for (int i = 0; i < World.getWidth(); i++) {
                coinPositions[j][i] = false;
            }
        }
        // until the last robot reaches the border, all robots move and scan the field
        while (scanRobots[scanRobots.length - 1].isFrontClear()) {
            for (int i = 0; i < scanRobots.length; i++) {
                // move and scan the field
                scanRobots[i].move();
                int j = scanRobots[i].getY();
                if (scanRobots[i].isOnACoin()) {
                    // i-th scan robot is on x=i+1 position
                    coinPositions[j][i + 1] = true;
                }
            }
        }
        // spin the robots, return to start positions, spin again
        spinRobots(scanRobots);
        returnRobots(scanRobots);
        spinRobots(scanRobots);
        return coinPositions;
    }

    /**
     * Performs one iteration of collecting coins, using the provided arrays to clean and determine where to clean.
     *
     * @param positionsOfCoins An array with all the coin positions to be collected
     * @param cleanRobots      An array containing the {@linkplain CleanRobot CleanRobots} to collect the coins with.
     */
    public void moveCleanRobots(CleanRobot[] cleanRobots, boolean[][] positionsOfCoins) {
        // TODO: H4.3
        // crash("H4.3 - remove if implemented");
        // until the last robot reaches the border, all robots move and pick a coin
        while (cleanRobots[cleanRobots.length - 1].isFrontClear()) {
            for (int j = 0; j < cleanRobots.length; j++) {
                // move and pick a coin
                cleanRobots[j].move();
                int i = cleanRobots[j].getX();
                // j-th clean robot is on y=j+1 position
                if (positionsOfCoins[j + 1][i]) {
                    cleanRobots[j].pickCoin();
                }
            }
        }
        // spin the robots, return to start positions, spin again
        spinRobots(cleanRobots);
        returnRobots(cleanRobots);
        spinRobots(cleanRobots);
    }

    /**
     * Collects all the coins in the world using all the previously implemented helper methods.
     */
    public void cleanWorld() {
        ScanRobot[] scanRobots = initScanRobots();
        CleanRobot[] cleanRobots = initCleaningRobots();
        boolean coinsGathered = false;
        while (!coinsGathered) {
            boolean[][] coinsInWorld = scanWorld(scanRobots);
            if (allCoinsGathered(coinsInWorld)) {
                break;
            }
            moveCleanRobots(cleanRobots, coinsInWorld);
            coinsGathered = allCoinsGathered(coinsInWorld);
        }
        System.out.println("Finished");
    }

    /**
     * Returns whether the provided array of coin positions contains at least one coin
     *
     * @param coins The array to search for coins
     * @return Whether the provided array contains at least one entry that is not false
     */
    public boolean allCoinsGathered(boolean[][] coins) {
        boolean allCoinsGathered = true;
        for (boolean[] coin : coins) {
            for (boolean b : coin) {
                if (b) {
                    allCoinsGathered = false;
                    break;
                }
            }
        }
        return allCoinsGathered;
    }
}

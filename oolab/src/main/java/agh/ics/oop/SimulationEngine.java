package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private List<Simulation> simulations;
    private List<Thread> threads = new ArrayList<>();
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);


    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runSync() {
        for (Simulation simulation : simulations) {
            simulation.run();
        }
    }

    public void runAsync() {

        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            threads.add(thread);
            thread.start();
        }
        awaitSimulationsEnd();
    }

    public void runAsyncInThreadPool() {
        for (Simulation simulation : simulations) {
            threadPool.submit(simulation);
        }
        awaitSimulationsEnd();
    }

    public void awaitSimulationsEnd() {
        try {
            for (Thread thread : threads) {
                thread.join(); // Oczekiwanie na zakończenie wątku
            }
            threadPool.shutdown();
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }


    private boolean isRunning = true;
    private final Object lock = new Object();

    public void run() {
        while (true) {
            synchronized (lock) {
                while (!isRunning) {
                    try {
                        lock.wait(); // Wait if paused
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            simulateStep();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }


    public void pause() {
        synchronized (lock) {
            isRunning = false;
        }
    }

    public void resume() {
        synchronized (lock) {
            isRunning = true;
            lock.notify();
        }
    }

    private void simulateStep() {
        System.out.println("Simulating step...");
        simulations.get(0).step();
    }
}

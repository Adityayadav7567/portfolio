package com.portfolio.compressor;

import com.portfolio.compressor.controller.CompressionController;import com.portfolio.compressor.storage.CsvHistoryRepository;import com.portfolio.compressor.services.HuffmanCodecService;import com.portfolio.compressor.view.DashboardView;
import javafx.application.Application;import javafx.scene.Scene;import javafx.stage.Stage;import java.nio.file.Path;

public final class MainApp extends Application {
    @Override public void start(Stage stage) {
        Path history = Path.of(System.getProperty("user.home"), ".huffman-compressor", "history.csv");
        var controller = new CompressionController(new HuffmanCodecService(new CsvHistoryRepository(history)));
        Scene scene = new Scene(new DashboardView(controller, history).root(), 1100, 720);
        scene.getStylesheets().add(getClass().getResource("/com/portfolio/compressor/styles.css").toExternalForm());
        stage.setTitle("Huffman File Compression Tool"); stage.setScene(scene); stage.show();
    }
    public static void main(String[] args) { launch(args); }
}

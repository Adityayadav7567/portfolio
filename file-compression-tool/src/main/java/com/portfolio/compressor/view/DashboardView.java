package com.portfolio.compressor.view;

import com.portfolio.compressor.controller.CompressionController;import javafx.geometry.*;import javafx.scene.Parent;import javafx.scene.chart.*;import javafx.scene.control.*;import javafx.scene.input.Dragboard;import javafx.scene.input.TransferMode;import javafx.scene.layout.*;import javafx.stage.FileChooser;import java.nio.file.Path;

/** Material-inspired JavaFX dashboard with drag/drop, progress, history, statistics and theme toggle. */
public final class DashboardView {
    private final CompressionController controller; private final Path history; private final BorderPane root = new BorderPane(); private final ProgressBar progress = new ProgressBar(0); private final Label status = new Label("Drop a file or choose an action.");
    public DashboardView(CompressionController controller, Path history) { this.controller = controller; this.history = history; build(); }
    public Parent root() { return root; }
    private void build() {
        VBox nav = new VBox(12, title("Dashboard"), button("Compress File", this::compress), button("Decompress File", this::decompress), button("Batch Compression", () -> info("Select multiple files from Compress File dialog.")), button("Compression History", () -> info("History: " + history)), button("Statistics", () -> root.setCenter(stats())), button("Settings", this::toggleTheme), button("About", () -> info("Huffman Coding compressor built with Java 21, JavaFX and Maven."))); nav.getStyleClass().add("nav");
        VBox center = new VBox(20, title("File Compression Tool"), new Label("Custom .huff compression using a hand-built Huffman tree, min heap, bit streams and SHA-256 validation."), progress, status); center.setPadding(new Insets(32)); center.getStyleClass().add("card");
        center.setOnDragOver(e -> { if (e.getDragboard().hasFiles()) e.acceptTransferModes(TransferMode.COPY); e.consume(); });
        center.setOnDragDropped(e -> { Dragboard db = e.getDragboard(); if (db.hasFiles()) compressPath(db.getFiles().getFirst().toPath()); e.setDropCompleted(true); e.consume(); });
        root.setLeft(nav); root.setCenter(center); root.getStyleClass().add("light");
    }
    private Button button(String text, Runnable action) { Button b = new Button(text); b.setMaxWidth(Double.MAX_VALUE); b.setOnAction(e -> action.run()); return b; }
    private Label title(String text) { Label l = new Label(text); l.getStyleClass().add("title"); return l; }
    private void compress() { FileChooser fc = new FileChooser(); var files = fc.showOpenMultipleDialog(root.getScene().getWindow()); if (files != null) files.forEach(f -> compressPath(f.toPath())); }
    private void compressPath(Path in) { Path out = in.resolveSibling(in.getFileName() + ".huff"); var task = controller.compressTask(in, out); progress.progressProperty().bind(task.progressProperty()); task.setOnSucceeded(e -> status.setText("Compressed to " + task.getValue().output() + " | saved " + String.format("%.2f%%", task.getValue().spaceSavedPercent()))); task.setOnFailed(e -> status.setText(task.getException().getMessage())); new Thread(task, "compress-ui-task").start(); }
    private void decompress() { FileChooser fc = new FileChooser(); var file = fc.showOpenDialog(root.getScene().getWindow()); if (file == null) return; var task = controller.decompressTask(file.toPath(), file.toPath().getParent()); progress.progressProperty().bind(task.progressProperty()); task.setOnSucceeded(e -> status.setText("Restored " + task.getValue())); task.setOnFailed(e -> status.setText(task.getException().getMessage())); new Thread(task, "decompress-ui-task").start(); }
    private Parent stats() { NumberAxis x = new NumberAxis(); NumberAxis y = new NumberAxis(); LineChart<Number, Number> c = new LineChart<>(x, y); c.setTitle("Compression Comparison Graph"); return new VBox(16, title("Statistics"), new Label("Total files, space saved, average ratio, fastest compression and largest file are persisted in history.csv."), c); }
    private void toggleTheme() { root.getStyleClass().setAll(root.getStyleClass().contains("dark") ? "light" : "dark"); }
    private void info(String m) { status.setText(m); }
}

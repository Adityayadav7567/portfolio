const metrics = [
  { label: "Original Size", value: "128.4 MB" },
  { label: "Compressed Size", value: "54.2 MB" },
  { label: "Space Saved", value: "57.8%" },
  { label: "Time Taken", value: "2.41s" },
];

const history = [
  ["resume.pdf", "4.2 MB", "1.9 MB", "54.7%"],
  ["dataset.csv", "96.8 MB", "38.6 MB", "60.1%"],
  ["source.zip", "27.4 MB", "13.7 MB", "50.0%"],
];

const navItems = [
  "Compress File",
  "Decompress File",
  "Batch Compression",
  "Compression History",
  "Statistics",
  "Settings",
  "About",
];

export const CompressionToolShowcase = () => {
  return (
    <section className="relative min-h-screen overflow-hidden bg-[#050816] px-6 py-24 text-white sm:px-10 lg:px-16">
      <div className="absolute left-1/2 top-0 h-[520px] w-[520px] -translate-x-1/2 rounded-full bg-cyan-500/20 blur-[130px]" />
      <div className="absolute bottom-0 right-0 h-[420px] w-[420px] rounded-full bg-purple-600/20 blur-[120px]" />

      <div className="relative mx-auto grid max-w-7xl gap-8 lg:grid-cols-[280px_1fr]">
        <aside className="rounded-[32px] border border-white/10 bg-white/[0.06] p-5 shadow-2xl shadow-cyan-950/30 backdrop-blur-xl">
          <div className="mb-8 rounded-3xl bg-gradient-to-br from-cyan-400 to-purple-500 p-5">
            <p className="text-sm font-medium uppercase tracking-[0.3em] text-white/80">
              Huffman Studio
            </p>
            <h1 className="mt-3 text-3xl font-bold">File Compression Tool</h1>
          </div>

          <nav className="space-y-3">
            {navItems.map((item, index) => (
              <button
                key={item}
                className={`w-full rounded-2xl px-4 py-3 text-left text-sm transition ${
                  index === 0
                    ? "bg-cyan-400 text-slate-950 shadow-lg shadow-cyan-400/25"
                    : "bg-white/5 text-slate-200 hover:bg-white/10"
                }`}
              >
                {item}
              </button>
            ))}
          </nav>
        </aside>

        <main className="space-y-8">
          <div className="grid gap-8 xl:grid-cols-[1fr_360px]">
            <div className="rounded-[36px] border border-white/10 bg-slate-950/70 p-8 shadow-2xl shadow-purple-950/30 backdrop-blur-xl">
              <div className="inline-flex rounded-full border border-cyan-400/30 bg-cyan-400/10 px-4 py-2 text-sm text-cyan-200">
                Java 21 • JavaFX • Huffman Coding • Custom Min Heap
              </div>

              <h2 className="mt-8 max-w-3xl text-5xl font-black leading-tight md:text-6xl">
                Compress files with a hand-built DSA engine.
              </h2>
              <p className="mt-5 max-w-2xl text-lg leading-8 text-slate-300">
                A resume-ready desktop experience for selecting files, tracking
                progress, validating decompression integrity, reviewing history,
                and explaining every algorithmic step clearly.
              </p>

              <div className="mt-8 grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
                {metrics.map((metric) => (
                  <div
                    key={metric.label}
                    className="rounded-3xl border border-white/10 bg-white/[0.04] p-5"
                  >
                    <p className="text-sm text-slate-400">{metric.label}</p>
                    <p className="mt-2 text-2xl font-bold text-cyan-200">
                      {metric.value}
                    </p>
                  </div>
                ))}
              </div>

              <div className="mt-8 rounded-[28px] border-2 border-dashed border-cyan-300/40 bg-cyan-300/5 p-8 text-center">
                <p className="text-5xl">📁</p>
                <h3 className="mt-4 text-2xl font-bold">Drop files here</h3>
                <p className="mt-2 text-slate-300">
                  Real app flow: drag and drop, choose compression level, then
                  stream bytes through Huffman encoding into a `.huff` file.
                </p>
                <div className="mt-6 h-3 overflow-hidden rounded-full bg-slate-800">
                  <div className="h-full w-[72%] rounded-full bg-gradient-to-r from-cyan-400 to-purple-500" />
                </div>
              </div>
            </div>

            <div className="space-y-8">
              <div className="rounded-[32px] border border-white/10 bg-white/[0.06] p-6 backdrop-blur-xl">
                <h3 className="text-xl font-bold">DSA Pipeline</h3>
                <ol className="mt-5 space-y-4 text-sm text-slate-300">
                  {[
                    "Count byte frequencies with streaming file reads",
                    "Build a binary min heap from non-zero symbols",
                    "Greedily merge nodes into a Huffman tree",
                    "Recursively generate prefix-free bit encodings",
                    "Pack and unpack bits with custom readers/writers",
                  ].map((step, index) => (
                    <li key={step} className="flex gap-3">
                      <span className="flex h-7 w-7 shrink-0 items-center justify-center rounded-full bg-purple-500/20 text-cyan-200">
                        {index + 1}
                      </span>
                      {step}
                    </li>
                  ))}
                </ol>
              </div>

              <div className="rounded-[32px] border border-white/10 bg-white/[0.06] p-6 backdrop-blur-xl">
                <h3 className="text-xl font-bold">Statistics</h3>
                <div className="mt-6 flex h-48 items-end gap-3">
                  {[45, 68, 52, 81, 63, 74].map((height, index) => (
                    <div key={index} className="flex flex-1 flex-col items-center gap-2">
                      <div
                        className="w-full rounded-t-xl bg-gradient-to-t from-purple-500 to-cyan-300"
                        style={{ height: `${height}%` }}
                      />
                      <span className="text-xs text-slate-400">F{index + 1}</span>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>

          <div className="grid gap-8 xl:grid-cols-[1fr_380px]">
            <div className="rounded-[32px] border border-white/10 bg-white/[0.06] p-6 backdrop-blur-xl">
              <h3 className="text-xl font-bold">Compression History</h3>
              <div className="mt-5 overflow-hidden rounded-2xl border border-white/10">
                <table className="w-full text-left text-sm">
                  <thead className="bg-white/10 text-slate-300">
                    <tr>
                      <th className="p-4">File</th>
                      <th className="p-4">Original</th>
                      <th className="p-4">Compressed</th>
                      <th className="p-4">Saved</th>
                    </tr>
                  </thead>
                  <tbody>
                    {history.map(([file, original, compressed, saved]) => (
                      <tr key={file} className="border-t border-white/10">
                        <td className="p-4 font-medium text-white">{file}</td>
                        <td className="p-4 text-slate-300">{original}</td>
                        <td className="p-4 text-slate-300">{compressed}</td>
                        <td className="p-4 text-cyan-200">{saved}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>

            <div className="rounded-[32px] border border-white/10 bg-gradient-to-br from-purple-600/30 to-cyan-500/20 p-6 backdrop-blur-xl">
              <h3 className="text-xl font-bold">Production Features</h3>
              <div className="mt-5 flex flex-wrap gap-3 text-sm">
                {[
                  "Integrity checks",
                  "Corruption handling",
                  "Batch jobs",
                  "Folder support",
                  "Dark / Light theme",
                  "Progress events",
                  "Clean MVC",
                  "JUnit tests",
                ].map((feature) => (
                  <span
                    key={feature}
                    className="rounded-full border border-white/10 bg-white/10 px-4 py-2 text-slate-100"
                  >
                    {feature}
                  </span>
                ))}
              </div>
            </div>
          </div>
        </main>
      </div>
    </section>
  );
};

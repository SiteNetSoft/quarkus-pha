import { defineConfig } from "@playwright/test";

const isCI = !!process.env.CI;
const workerOverride = process.env.PW_WORKERS
  ? Number(process.env.PW_WORKERS)
  : undefined;

export default defineConfig({
  testDir: "./tests",
  timeout: 30_000,
  expect: { timeout: 5_000 },
  fullyParallel: !isCI,
  forbidOnly: isCI,
  retries: isCI ? 2 : 0,
  workers: workerOverride ?? (isCI ? 1 : undefined),
  reporter: [
    ["html", { open: "never" }],
    ["list"]
  ],
  use: {
    baseURL: "http://localhost:9090",
    trace: "on-first-retry",
    screenshot: "only-on-failure"
  },
  projects: [
    {
      name: "chromium",
      use: { browserName: "chromium" }
    }
  ]
});

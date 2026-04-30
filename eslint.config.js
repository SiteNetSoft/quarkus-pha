export default [
  {
    files: ["runtime/src/main/resources/META-INF/resources/web/js/**/*.js"],
    ignores: [
      "**/vendor/**",
      "**/node_modules/**",
      "**/build/**"
    ],
    languageOptions: {
      ecmaVersion: "latest",
      sourceType: "module",
      globals: {
        // Browser globals
        window: "readonly",
        document: "readonly",
        console: "readonly",
        setTimeout: "readonly",
        setInterval: "readonly",
        clearTimeout: "readonly",
        clearInterval: "readonly",
        fetch: "readonly",
        URLSearchParams: "readonly",
        FormData: "readonly",
        CustomEvent: "readonly",
        Event: "readonly",
        HTMLElement: "readonly",
        MutationObserver: "readonly",
        requestAnimationFrame: "readonly",
        // Stack globals
        Alpine: "readonly",
        htmx: "readonly",
        echarts: "readonly",
        d3: "readonly",
        maplibregl: "readonly"
      }
    },
    rules: {
      "no-unused-vars": ["warn", { argsIgnorePattern: "^_" }],
      "no-undef": "error",
      "no-console": "off",
      "prefer-const": "warn",
      "eqeqeq": ["error", "always"]
    }
  }
];

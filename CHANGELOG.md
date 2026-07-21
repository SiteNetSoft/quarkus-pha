# Changelog

All notable changes to this project are documented in this file.
Format: [Keep a Changelog](https://keepachangelog.com/en/1.1.0/);
versioning: [SemVer](https://semver.org/).

## [Unreleased]

### Added
- Typed Java component models for all PatternFly component buckets (builder-style,
  `@TemplateData`, zero template shadowing) alongside the Qute include shells.
- GraalVM native-image support, validated against the full Playwright suite.
- Extension catalog metadata, contribution/security/changelog docs, C4 architecture
  diagrams in the README.
- CI that mirrors the local `e2e.sh` pipeline.

### Changed
- Quarkus 3.37.3, PatternFly 6.6.0, Font Awesome 7.3.1, Playwright 1.61.1.
- Demo sample media no longer ships inside the extension JAR (moved to the
  integration-tests demo app).

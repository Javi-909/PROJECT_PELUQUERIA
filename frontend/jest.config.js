module.exports = {
  preset: 'jest-preset-angular',
  // Apuntamos ÚNICAMENTE a nuestro archivo manual
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  testPathIgnorePatterns: ['<rootDir>/node_modules/', '<rootDir>/dist/']
};
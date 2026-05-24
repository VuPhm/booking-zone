/** @type {import('next').NextConfig} */
const nextConfig = {
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://localhost:8080/api/:path*', // Bản đồ hóa URL để tránh xung đột Cross-Origin
      },
    ];
  },
};

export default nextConfig;
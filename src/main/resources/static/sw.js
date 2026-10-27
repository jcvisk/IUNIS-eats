const CACHE_NAME = 'iunis-eats-v1';
const ASSETS_TO_CACHE = [
    '/css/app.css',
    '/images/logo-iunis-eats.png'
];

self.addEventListener('install', (event) => {
    event.waitUntil(
        caches.open(CACHE_NAME).then((cache) => {
            return cache.addAll(ASSETS_TO_CACHE);
        })
    );
});

self.addEventListener('fetch', (event) => {
    // Solo interceptar recursos estáticos
    if (event.request.url.includes('/css/') || event.request.url.includes('/images/')) {
        event.respondWith(
            caches.match(event.request).then((response) => {
                return response || fetch(event.request);
            })
        );
    } else {
        // Para HTML o peticiones a base de datos, solo dejar pasar a la red.
        // Si no hay red, avisar limpiamente.
        event.respondWith(
            fetch(event.request).catch(() => {
                return new Response("Sin conexión a internet", {
                    status: 503,
                    statusText: "Service Unavailable"
                });
            })
        );
    }
});
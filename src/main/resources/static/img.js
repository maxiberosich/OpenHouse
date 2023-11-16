var mySwiper = new Swiper('.swiper-container', {
    loop: true, // Opcional: permite que el carrusel sea infinito
    slidesPerView: 1, // Número de diapositivas visibles a la vez
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev'
    },
    pagination: {
        el: '.swiper-pagination',
        clickable: true // Opcional: permite hacer clic en la paginación
    }
});


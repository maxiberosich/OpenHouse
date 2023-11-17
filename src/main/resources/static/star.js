const stars= document.querySelectorAll('.star');

stars.forEach(function(star,calificacion){
star.addEventListener('click', function(){
    for (let i=0; i<=calificacion; i++){
        stars[i].classList.add('checked');
    }
    for (let i =calificacion+1; i < stars.length; i++) {
        stars[i].classList.remove('checked');
        
    }
})

})

        // Función para cambiar la calificación al hacer clic en las estrellas
        function changeRating(value) {
            const checkbox = document.getElementById('ratingCheckbox');
            checkbox.value = value;
            console.alert=("El valor es: ");
        }

    
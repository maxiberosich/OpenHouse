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
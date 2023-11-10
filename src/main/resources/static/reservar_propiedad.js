
function calcularDiasDeReserva() {
    const fechaInicioInput = document.getElementById("fechaInicio");
    const fechaFinInput = document.getElementById("fechaFin");
    const diasReservaElement = document.getElementById("diasReserva");
    const precioBaseElement = document.getElementById("precioBase");
    const precioFinalElement = document.getElementById("precioFinal");


    if (fechaInicioInput.value && fechaFinInput.value) {
        const fechaInicio = new Date(fechaInicioInput.value);
        const fechaFin = new Date(fechaFinInput.value);

        const diferenciaEnMilisegundos = fechaFin - fechaInicio;

        const dias = Math.ceil(diferenciaEnMilisegundos / (1000 * 60 * 60 * 24));
       
        const precioFinal= precioBaseElement.value * dias; 

        diasReservaElement.textContent = `Total de días reservados: ${dias}`;
        precioFinalElement.textContent = `Total Precio de reserva: $ ${precioFinal}`;

    } else {
        diasReservaElement.textContent = "Selecciona ambas fechas para calcular los días de reserva.";
    }
}
document.getElementById("fechaInicio").addEventListener("change", calcularDiasDeReserva);
document.getElementById("fechaFin").addEventListener("change", calcularDiasDeReserva);


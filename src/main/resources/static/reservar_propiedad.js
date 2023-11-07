
function calcularCantidad() {
    const adultos = parseInt(document.getElementById("adultos").value);
    const ninos = parseInt(document.getElementById("ninos").value);
    const bebes = parseInt(document.getElementById("bebes").value);
    const totalPersonas = adultos + ninos + bebes;

    document.getElementById("totalPersonas").textContent = totalPersonas;
}


function calcularDiasDeReserva() {
    const fechaInicioInput = document.getElementById("fechaInicio");
    const fechaFinInput = document.getElementById("fechaFin");
    const diasReservaElement = document.getElementById("diasReserva");


    if (fechaInicioInput.value && fechaFinInput.value) {
        const fechaInicio = new Date(fechaInicioInput.value);
        const fechaFin = new Date(fechaFinInput.value);

        const diferenciaEnMilisegundos = fechaFin - fechaInicio;

        const dias = Math.ceil(diferenciaEnMilisegundos / (1000 * 60 * 60 * 24));

        diasReservaElement.textContent = `Total de días reservados: ${dias}`;
    } else {
        diasReservaElement.textContent = "Selecciona ambas fechas para calcular los días de reserva.";
    }
}
document.getElementById("fechaInicio").addEventListener("change", calcularDiasDeReserva);
document.getElementById("fechaFin").addEventListener("change", calcularDiasDeReserva);
    
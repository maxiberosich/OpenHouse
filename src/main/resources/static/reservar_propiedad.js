
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

        const precioFinal = precioBaseElement.value * dias;

        diasReservaElement.textContent = `Total de días reservados: ${dias}`;
        precioFinalElement.textContent = `Total Precio de reserva: $ ${precioFinal}`;

    } else {
        diasReservaElement.textContent = "Selecciona ambas fechas para calcular los días de reserva.";
    }
}
document.getElementById("fechaInicio").addEventListener("change", calcularDiasDeReserva);
document.getElementById("fechaFin").addEventListener("change", calcularDiasDeReserva);


const dateInicioInput = document.getElementById('fechaInicio');
const dateFinInput = document.getElementById('fechaFin');
const submitButton = document.getElementById('submitButton');
const message = document.getElementById('message');
const fechas = document.getElementById('fechas');
const cadena = fechas.value.toString();
const cadenaRes = cadena.replaceAll('[', '');
const cadenaRes2 = cadenaRes.toString().replaceAll(']', '');
const arrayCadena = cadenaRes2.toString().split(',');
const arrayCadena2 = arrayCadena.toString().replaceAll(' ', '');
const fechasReservadas = [];

const cadenaJson = JSON.stringify(arrayCadena2);

dateInicioInput.addEventListener('change', () => {
    const selectedDate = dateInicioInput.value;
    const isReserved = cadenaJson.includes(selectedDate);
    console.log(dateInicioInput.value);
    if (isReserved) {
        message.textContent = 'La fecha está reservada';
        message.classList.add('error-mess');
        submitButton.disabled = true;
    } else {
        message.textContent = 'La fecha está disponible';
        message.classList.add('error-mess1');
        submitButton.disabled = false;
    }
});

dateFinInput.addEventListener('change', () => {
    const fechaSeleccionada = dateFinInput.value;
    const reservado = cadenaJson.includes(fechaSeleccionada);

    if (reservado) {
        message.textContent = 'La fecha está reservada';
        message.classList.add('error-mess');
        submitButton.disabled = true;
    } else {
        message.textContent = 'La fecha está disponible';
        message.classList.add('error-mess1');
        submitButton.disabled = false;
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const fechaReal = document.getElementById('fechaReal');
    const fechaVisible = document.getElementById('fechaVisible');

    // Establecer fecha actual si no hay valor
    if (!fechaReal.value) {
        const hoy = new Date().toISOString().substring(0, 10);
        fechaReal.value = hoy;
    }

    // Sincronizar campos
    fechaVisible.value = fechaReal.value;
});


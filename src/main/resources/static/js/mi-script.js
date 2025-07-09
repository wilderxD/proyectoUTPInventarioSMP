/*<![CDATA[*/
function confirmarEliminacion(event) {
    event.preventDefault();
    const url = event.currentTarget.getAttribute('href');

    Swal.fire({
        title: '¿Eliminar empleado?',
        text: "¡Esta acción no se puede deshacer!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
        allowOutsideClick: false
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = url;
        }
    });
}

// Mostrar alerta de éxito si existe el mensaje flash
window.onload = function () {
    const successMessage = /*[[${success}]]*/ null;
    if (successMessage) {
        Swal.fire({
            title: '¡Éxito!',
            text: successMessage,
            icon: 'success',
            confirmButtonColor: '#3085d6'
        });
    }
};

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


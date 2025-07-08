function confirmarEliminacion(event) {
    event.preventDefault(); // Detiene la navegación inmediata
    const url = event.currentTarget.getAttribute('href'); // Captura la URL del enlace

    Swal.fire({
        title: '¿Estás seguro?',
        text: "¡No podrás revertir esta acción!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = url; // Redirige solo si confirma
        }
    });
}



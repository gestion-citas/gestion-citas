// Inicialización de componentes cuando el DOM esté listo
document.addEventListener('DOMContentLoaded', function() {
    // Inicializar DataTables
    initializeDatatables();
    
    // Inicializar tooltips de Bootstrap
    initializeTooltips();
    
    // Inicializar validación de formularios
    initializeFormValidation();
    
    // Inicializar manejadores de eventos
    initializeEventHandlers();
});

// Inicialización de DataTables
function initializeDatatables() {
    const tables = document.querySelectorAll('.datatable');
    tables.forEach(table => {
        new DataTable(table, {
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.7/i18n/es-ES.json'
            },
            responsive: true,
            pageLength: 10,
            order: [[0, 'desc']]
        });
    });
}

// Inicialización de tooltips
function initializeTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

// Validación de formularios
function initializeFormValidation() {
    const forms = document.querySelectorAll('.needs-validation');
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });
}

// Manejadores de eventos
function initializeEventHandlers() {
    // Confirmar cancelación de cita
    const cancelButtons = document.querySelectorAll('.btn-cancelar-cita');
    cancelButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('¿Está seguro de que desea cancelar esta cita?')) {
                e.preventDefault();
            }
        });
    });
    
    }
}

// Función para mostrar mensajes de error
function mostrarError(mensaje) {
    const alertsContainer = document.getElementById('alerts-container');
    if (alertsContainer) {
        const alert = document.createElement('div');
        alert.className = 'alert alert-danger alert-dismissible fade show';
        alert.innerHTML = `
            ${mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        alertsContainer.appendChild(alert);
        
        // Eliminar la alerta después de 5 segundos
        setTimeout(() => {
            alert.remove();
        }, 5000);
    }
}

// Función para mostrar mensajes de éxito
function mostrarExito(mensaje) {
    const alertsContainer = document.getElementById('alerts-container');
    if (alertsContainer) {
        const alert = document.createElement('div');
        alert.className = 'alert alert-success alert-dismissible fade show';
        alert.innerHTML = `
            ${mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        alertsContainer.appendChild(alert);
        
        // Eliminar la alerta después de 3 segundos
        setTimeout(() => {
            alert.remove();
        }, 3000);
    }
}
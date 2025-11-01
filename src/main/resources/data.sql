-- ==========================================
-- DATOS INICIALES - ESPECIALIDADES
-- ==========================================
INSERT INTO especialidad (nombre, descripcion, activo) VALUES
('Cardiología', 'Especialidad médica que se encarga del estudio, diagnóstico y tratamiento de las enfermedades del corazón y del aparato circulatorio', true),
('Dermatología', 'Especialidad médica que se ocupa del diagnóstico y tratamiento de las enfermedades de la piel, cabello y uñas', true),
('Ginecología', 'Especialidad médica que trata las enfermedades del sistema reproductor femenino', true),
('Neurología', 'Especialidad médica que trata los trastornos del sistema nervioso central y periférico', true),
('Pediatría', 'Especialidad médica que estudia al niño y sus enfermedades desde el nacimiento hasta la adolescencia', true),
('Traumatología', 'Especialidad médica que se dedica al estudio de las lesiones del aparato locomotor', true),
('Oftalmología', 'Especialidad médica que estudia las enfermedades de los ojos y su tratamiento', true),
('Otorrinolaringología', 'Especialidad médica que trata las enfermedades del oído, nariz y garganta', true),
('Psiquiatría', 'Especialidad médica dedicada al estudio y tratamiento de los trastornos mentales', true),
('Endocrinología', 'Especialidad médica que estudia las glándulas endocrinas y las hormonas', true);

-- ==========================================
-- DATOS INICIALES - USUARIOS
-- ==========================================
-- Contraseñas en texto plano para el sistema actual
INSERT INTO usuario (username, password, email, role, activo) VALUES
('admin', 'admin123', 'admin@hospital.com', 'ADMIN', true),
('medico1', 'medico123', 'medico1@hospital.com', 'MEDICO', true),
('medico2', 'medico123', 'medico2@hospital.com', 'MEDICO', true),
('recepcion1', 'recepcion123', 'recepcion@hospital.com', 'RECEPCION', true);

-- ==========================================
-- DATOS INICIALES - MÉDICOS
-- ==========================================
INSERT INTO medico (dni, nombres, apellidos, email, telefono, id_especialidad, id_usuario, activo) VALUES
('87654321', 'María', 'García', 'maria.garcia@hospital.com', '987654322', 2, 2, true),
('44332211', 'Ana', 'Lopez', 'ana.lopez@hospital.com', '987654324', 4, 3, true),
('11223344', 'Luis', 'Martinez', 'luis.martinez@hospital.com', '987654323', 5, NULL, false),
('12345678', 'Carlos', 'Rodriguez', 'carlos.rodriguez@hospital.com', '987654321', 1, NULL, false),
('55667788', 'Pedro', 'Sanchez', 'pedro.sanchez@hospital.com', '987654325', 3, NULL, false);

-- ==========================================
-- DATOS INICIALES - PACIENTES
-- ==========================================
INSERT INTO paciente (dni, nombres, apellidos, email, telefono, fecha_nacimiento, direccion) VALUES
('12345678', 'Juan', 'Pérez', 'juan.perez@gmail.com', '987654321', '1990-05-15', 'Av. Principal 123, Lima'),
('87654321', 'María', 'López', 'maria.lopez@gmail.com', '987654322', '1985-08-20', 'Jr. Las Flores 456, Lima'),
('11223344', 'Carlos', 'Ramírez', 'carlos.ramirez@gmail.com', '987654323', '1995-03-10', 'Calle Los Pinos 789, Callao');

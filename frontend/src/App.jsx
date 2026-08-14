import { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

const API = "http://localhost:8080/api";

function App() {
    const [alumnos, setAlumnos] = useState([]);
    const [alumnoId, setAlumnoId] = useState("");

    const [cursos, setCursos] = useState([]);
    const [cursoSeleccionado, setCursoSeleccionado] = useState("");
    const [turno, setTurno] = useState("");

    const [carrito, setCarrito] = useState([]);
    const [mensaje, setMensaje] = useState("");
    const [tipoMensaje, setTipoMensaje] = useState("");

    // ============================
    // CARGAR ALUMNOS
    // ============================

    useEffect(() => {
        cargarAlumnos();
    }, []);

    const cargarAlumnos = async () => {
        try {
            const response = await axios.get(`${API}/alumnos`);
            setAlumnos(response.data);
        } catch (error) {
            console.error(error);
            mostrarMensaje(
                "No se pudieron cargar los alumnos",
                "error"
            );
        }
    };

    // ============================
    // SELECCIONAR ALUMNO
    // ============================

    const cambiarAlumno = async (e) => {
        const id = e.target.value;

        setAlumnoId(id);
        setCursos([]);
        setCursoSeleccionado("");
        setTurno("");
        setCarrito([]);
        setMensaje("");

        if (!id) {
            return;
        }

        const alumno = alumnos.find(
            (a) => a.id.toString() === id
        );

        if (!alumno) {
            return;
        }

        try {
            const response = await axios.get(
                `${API}/cursos?ciclo=${alumno.ciclo}`
            );

            setCursos(response.data);

        } catch (error) {
            console.error(error);

            mostrarMensaje(
                "No se pudieron cargar los cursos",
                "error"
            );
        }
    };

    // ============================
    // ALUMNO ACTUAL
    // ============================

    const alumnoActual = alumnos.find(
        (alumno) =>
            alumno.id.toString() === alumnoId
    );

    // ============================
    // CURSO ACTUAL
    // ============================

    const cursoActual = cursos.find(
        (curso) =>
            curso.id.toString() === cursoSeleccionado
    );

    // ============================
    // OBTENER HORARIO
    // ============================

    const obtenerHorario = (curso, turnoSeleccionado) => {

        if (!curso || !turnoSeleccionado) {
            return null;
        }

        switch (turnoSeleccionado) {

            case "MANANA":
                return {
                    inicio: curso.horaInicioManana,
                    fin: curso.horaFinManana
                };

            case "TARDE":
                return {
                    inicio: curso.horaInicioTarde,
                    fin: curso.horaFinTarde
                };

            case "NOCHE":
                return {
                    inicio: curso.horaInicioNoche,
                    fin: curso.horaFinNoche
                };

            default:
                return null;
        }
    };

    // ============================
    // FORMATEAR HORA
    // ============================

    const formatearHora = (hora) => {

        if (!hora) {
            return "--:--";
        }

        return hora.substring(0, 5);
    };

    // ============================
    // NOMBRE DEL TURNO
    // ============================

    const nombreTurno = (turnoSeleccionado) => {

        switch (turnoSeleccionado) {

            case "MANANA":
                return "Mañana";

            case "TARDE":
                return "Tarde";

            case "NOCHE":
                return "Noche";

            default:
                return "";
        }
    };

    // ============================
    // COMPROBAR CRUCE
    // ============================

    const existeCruce = (nuevoCurso, nuevoTurno) => {

        const nuevoHorario =
            obtenerHorario(
                nuevoCurso,
                nuevoTurno
            );

        if (!nuevoHorario) {
            return false;
        }

        return carrito.some((item) => {

            if (item.turno !== nuevoTurno) {
                return false;
            }

            const inicioNuevo =
                convertirMinutos(
                    nuevoHorario.inicio
                );

            const finNuevo =
                convertirMinutos(
                    nuevoHorario.fin
                );

            const inicioExistente =
                convertirMinutos(
                    item.inicio
                );

            const finExistente =
                convertirMinutos(
                    item.fin
                );

            return (
                inicioNuevo < finExistente &&
                finNuevo > inicioExistente
            );
        });
    };

    // ============================
    // CONVERTIR HORA A MINUTOS
    // ============================

    const convertirMinutos = (hora) => {

        if (!hora) {
            return 0;
        }

        const partes = hora.substring(0, 5).split(":");

        return (
            Number(partes[0]) * 60 +
            Number(partes[1])
        );
    };

    // ============================
    // AGREGAR CURSO
    // ============================

    const agregarCurso = () => {

        if (!alumnoId) {
            mostrarMensaje(
                "Seleccione un alumno",
                "error"
            );
            return;
        }

        if (!cursoSeleccionado) {
            mostrarMensaje(
                "Seleccione un curso",
                "error"
            );
            return;
        }

        if (!turno) {
            mostrarMensaje(
                "Seleccione un turno",
                "error"
            );
            return;
        }

        if (!cursoActual) {
            mostrarMensaje(
                "Curso no encontrado",
                "error"
            );
            return;
        }

        // Evitar curso repetido
        const yaExiste = carrito.some(
            (item) =>
                item.cursoId === cursoActual.id
        );

        if (yaExiste) {
            mostrarMensaje(
                "El curso ya está en el carrito",
                "error"
            );
            return;
        }

        const horario =
            obtenerHorario(
                cursoActual,
                turno
            );

        if (!horario) {
            mostrarMensaje(
                "El curso no tiene horario configurado para este turno",
                "error"
            );
            return;
        }

        // Validar cruce antes de agregar
        if (existeCruce(cursoActual, turno)) {
            mostrarMensaje(
                "Existe un cruce de horario con otro curso seleccionado",
                "error"
            );
            return;
        }

        const nuevoCurso = {
            cursoId: cursoActual.id,
            cursoNombre: cursoActual.nombre,
            turno: turno,
            inicio: horario.inicio,
            fin: horario.fin
        };

        setCarrito((prev) => [
            ...prev,
            nuevoCurso
        ]);

        setCursoSeleccionado("");
        setTurno("");

        mostrarMensaje(
            "Curso agregado correctamente",
            "success"
        );
    };

    // ============================
    // ELIMINAR CURSO
    // ============================

    const eliminarCurso = (index) => {

        setCarrito((prev) =>
            prev.filter(
                (_, i) => i !== index
            )
        );

        setMensaje("");
    };

    // ============================
    // GUARDAR MATRÍCULA
    // ============================

    const guardarMatricula = async () => {

        if (!alumnoId) {
            mostrarMensaje(
                "Seleccione un alumno",
                "error"
            );
            return;
        }

        if (carrito.length === 0) {
            mostrarMensaje(
                "Debe seleccionar al menos un curso",
                "error"
            );
            return;
        }

        try {

            const request = {
                alumnoId: Number(alumnoId),

                cursos: carrito.map((item) => ({
                    cursoId: item.cursoId,
                    turno: item.turno
                }))
            };

            console.log(
                "Solicitud enviada:",
                request
            );

            const response = await axios.post(
                `${API}/matriculas`,
                request
            );

            mostrarMensaje(
                response.data,
                "success"
            );

            setCarrito([]);

        } catch (error) {

            console.error(error);

            mostrarMensaje(
                error.response?.data ||
                "Error al guardar la matrícula",
                "error"
            );
        }
    };

    // ============================
    // MENSAJES
    // ============================

    const mostrarMensaje = (
        texto,
        tipo
    ) => {

        setMensaje(texto);
        setTipoMensaje(tipo);
    };

    // ============================
    // HORARIO ACTUAL
    // ============================

    const horarioActual =
        obtenerHorario(
            cursoActual,
            turno
        );

    // ============================
    // RENDER
    // ============================

    return (
        <div className="app">

            <div className="container">

                <header className="header">

                    <h1>
                        Sistema de Matrícula
                    </h1>

                    <p>
                        Registro de matrícula por ciclo y turno
                    </p>

                </header>

                {/* ========================= */}
                {/* DATOS DEL ALUMNO */}
                {/* ========================= */}

                <section className="card">

                    <h2>
                        Datos del alumno
                    </h2>

                    <div className="formulario">

                        <div className="campo">

                            <label>
                                Alumno
                            </label>

                            <select
                                value={alumnoId}
                                onChange={cambiarAlumno}
                            >

                                <option value="">
                                    Seleccione un alumno
                                </option>

                                {alumnos.map(
                                    (alumno) => (

                                        <option
                                            key={alumno.id}
                                            value={alumno.id}
                                        >
                                            {alumno.nombre}
                                        </option>

                                    )
                                )}

                            </select>

                        </div>

                        <div className="campo">

                            <label>
                                Ciclo
                            </label>

                            <input
                                type="text"
                                value={
                                    alumnoActual
                                        ? `Ciclo ${alumnoActual.ciclo}`
                                        : ""
                                }
                                placeholder="Se obtiene del alumno"
                                disabled
                            />

                        </div>

                    </div>

                </section>

                {/* ========================= */}
                {/* SELECCIONAR CURSO */}
                {/* ========================= */}

                <section className="card">

                    <h2>
                        Seleccionar curso
                    </h2>

                    <div className="formulario">

                        <div className="campo">

                            <label>
                                Curso
                            </label>

                            <select
                                value={cursoSeleccionado}
                                onChange={(e) =>
                                    setCursoSeleccionado(
                                        e.target.value
                                    )
                                }
                                disabled={!alumnoId}
                            >

                                <option value="">
                                    Seleccione un curso
                                </option>

                                {cursos.map(
                                    (curso) => (

                                        <option
                                            key={curso.id}
                                            value={curso.id}
                                        >
                                            {curso.nombre}
                                        </option>

                                    )
                                )}

                            </select>

                        </div>

                        <div className="campo">

                            <label>
                                Turno
                            </label>

                            <select
                                value={turno}
                                onChange={(e) =>
                                    setTurno(
                                        e.target.value
                                    )
                                }
                                disabled={
                                    !cursoSeleccionado
                                }
                            >

                                <option value="">
                                    Seleccione un turno
                                </option>

                                <option value="MANANA">
                                    Mañana
                                </option>

                                <option value="TARDE">
                                    Tarde
                                </option>

                                <option value="NOCHE">
                                    Noche
                                </option>

                            </select>

                        </div>

                    </div>

                    {/* HORARIOS DEL CURSO */}

                    {cursoActual && (
                        <div className="horarios">

                            <h3>
                                Horarios disponibles
                            </h3>

                            <div className="horarios-grid">

                                <div
                                    className={
                                        "horario " +
                                        (
                                            turno === "MANANA"
                                                ? "seleccionado"
                                                : ""
                                        )
                                    }
                                    onClick={() =>
                                        setTurno("MANANA")
                                    }
                                >

                                    <strong>
                                        Mañana
                                    </strong>

                                    <span>
                                        {
                                            formatearHora(
                                                cursoActual.horaInicioManana
                                            )
                                        }
                                        {" - "}
                                        {
                                            formatearHora(
                                                cursoActual.horaFinManana
                                            )
                                        }
                                    </span>

                                </div>

                                <div
                                    className={
                                        "horario " +
                                        (
                                            turno === "TARDE"
                                                ? "seleccionado"
                                                : ""
                                        )
                                    }
                                    onClick={() =>
                                        setTurno("TARDE")
                                    }
                                >

                                    <strong>
                                        Tarde
                                    </strong>

                                    <span>
                                        {
                                            formatearHora(
                                                cursoActual.horaInicioTarde
                                            )
                                        }
                                        {" - "}
                                        {
                                            formatearHora(
                                                cursoActual.horaFinTarde
                                            )
                                        }
                                    </span>

                                </div>

                                <div
                                    className={
                                        "horario " +
                                        (
                                            turno === "NOCHE"
                                                ? "seleccionado"
                                                : ""
                                        )
                                    }
                                    onClick={() =>
                                        setTurno("NOCHE")
                                    }
                                >

                                    <strong>
                                        Noche
                                    </strong>

                                    <span>
                                        {
                                            formatearHora(
                                                cursoActual.horaInicioNoche
                                            )
                                        }
                                        {" - "}
                                        {
                                            formatearHora(
                                                cursoActual.horaFinNoche
                                            )
                                        }
                                    </span>

                                </div>

                            </div>

                        </div>
                    )}

                    {/* HORARIO SELECCIONADO */}

                    {horarioActual && turno && (

                        <div className="horario-seleccionado">

                            <div>

                                <span>
                                    Turno seleccionado
                                </span>

                                <strong>
                                    {nombreTurno(turno)}
                                </strong>

                            </div>

                            <div>

                                <span>
                                    Horario
                                </span>

                                <strong>
                                    {
                                        formatearHora(
                                            horarioActual.inicio
                                        )
                                    }
                                    {" - "}
                                    {
                                        formatearHora(
                                            horarioActual.fin
                                        )
                                    }
                                </strong>

                            </div>

                        </div>

                    )}

                    <button
                        className="btn-primary"
                        type="button"
                        onClick={agregarCurso}
                        disabled={
                            !alumnoId ||
                            !cursoSeleccionado ||
                            !turno
                        }
                    >
                        Agregar curso
                    </button>

                </section>

                {/* ========================= */}
                {/* CARRITO */}
                {/* ========================= */}

                <section className="card">

                    <div className="titulo-carrito">

                        <div>

                            <h2>
                                Cursos seleccionados
                            </h2>

                            <p>
                                Revise su selección antes de guardar
                            </p>

                        </div>

                        <div className="contador">
                            {carrito.length}
                        </div>

                    </div>

                    {carrito.length === 0 ? (

                        <div className="carrito-vacio">

                            <p>
                                No hay cursos seleccionados.
                            </p>

                        </div>

                    ) : (

                        <div className="tabla-container">

                            <table>

                                <thead>

                                    <tr>

                                        <th>
                                            Curso
                                        </th>

                                        <th>
                                            Turno
                                        </th>

                                        <th>
                                            Horario
                                        </th>

                                        <th>
                                            Acción
                                        </th>

                                    </tr>

                                </thead>

                                <tbody>

                                    {carrito.map(
                                        (item, index) => (

                                            <tr key={index}>

                                                <td>
                                                    <strong>
                                                        {item.cursoNombre}
                                                    </strong>
                                                </td>

                                                <td>
                                                    <span className="badge">
                                                        {
                                                            nombreTurno(
                                                                item.turno
                                                            )
                                                        }
                                                    </span>
                                                </td>

                                                <td>
                                                    {
                                                        formatearHora(
                                                            item.inicio
                                                        )
                                                    }
                                                    {" - "}
                                                    {
                                                        formatearHora(
                                                            item.fin
                                                        )
                                                    }
                                                </td>

                                                <td>

                                                    <button
                                                        className="btn-delete"
                                                        type="button"
                                                        onClick={() =>
                                                            eliminarCurso(
                                                                index
                                                            )
                                                        }
                                                    >
                                                        Eliminar
                                                    </button>

                                                </td>

                                            </tr>

                                        )
                                    )}

                                </tbody>

                            </table>

                        </div>

                    )}

                    {/* MENSAJE */}

                    {mensaje && (

                        <div
                            className={
                                `mensaje ${tipoMensaje}`
                            }
                        >
                            {mensaje}
                        </div>

                    )}

                    <button
                        className="btn-guardar"
                        type="button"
                        onClick={guardarMatricula}
                        disabled={
                            !alumnoId ||
                            carrito.length === 0
                        }
                    >
                        Guardar matrícula
                    </button>

                </section>

            </div>

        </div>
    );
}

export default App;
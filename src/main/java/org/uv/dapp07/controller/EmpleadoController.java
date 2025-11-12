package org.uv.dapp07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.uv.dapp07.models.Empleado;
import org.uv.dapp07.repository.EmpleadoRepository;

import java.util.List;
import java.util.Optional;

// <>
@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoRepository empRep;

    @Autowired
    public EmpleadoController(EmpleadoRepository empRep) {
        this.empRep = empRep;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Empleado> postEmpleado(@RequestBody Empleado empleado) {
        Empleado empSaved = empRep.save(empleado);
        empleado.setId(empSaved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
    }

    @GetMapping
    public List<Empleado> listar() {
        return empRep.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> findById(@PathVariable Long id) {
         Optional<Empleado> emp = empRep.findById(id);
        return emp.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> putEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        Optional<Empleado> emp = empRep.findById(id);
        if (emp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Empleado empleado1 = emp.get();
        empleado1.setNombre(empleado.getNombre());
        empleado1.setDireccion(empleado.getDireccion());
        empleado1.setTelefono(empleado.getTelefono());

        empRep.save(empleado1);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleado1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empleado> deleteEmpleado(@PathVariable Long id) {

        Optional<Empleado> emp = empRep.findById(id);
        if (emp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        empRep.delete(emp.get());
        return ResponseEntity.ok(emp.get());
    }

}

package com.mysoftwareproject.manager;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping
    public Manager createManager(@RequestBody ManagerDto managerDto) {
        return managerService.createManager(managerDto);
    }

    @GetMapping
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/getManagerById/{managerId}")
    public Manager getManagerById(@PathVariable Integer managerId) {
        return managerService.getManagerById(managerId);
    }

    @PutMapping("/{managerId}")
    public Manager updateManager(@PathVariable("managerId") Integer managerId, @RequestBody ManagerDto managerDto) {
        return managerService.updateManager(managerId, managerDto);
    }

}

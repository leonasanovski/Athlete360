package sorsix.internship.backend.security.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.internship.backend.security.dtos.LoginRequest
import sorsix.internship.backend.security.dtos.RegisterRequest
import sorsix.internship.backend.security.repository.AppUserRepository
import sorsix.internship.backend.security.service.UserService

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = ["http://localhost:4200"])
class UserController(
    private val userService: UserService,
    private val userRepository: AppUserRepository,

) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<Any> =
        try {
            val registeredUser = userService.register(request)
            val loginRequest = LoginRequest(embg = registeredUser.embg, password = request.password)
            ResponseEntity.ok(userService.login(loginRequest))
//            ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request))
        } catch (e: RuntimeException) {
            ResponseEntity.badRequest().body(e.message ?: "Registration error")
        }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> =
        try {
            val u = userRepository.findByEmbg(request.embg.trim()) ?: error("no user")
            println("Korisnik: $u")
            ResponseEntity.ok(userService.login(request))
        } catch (e: RuntimeException) {
            println("There is an error caught: ${e.message}")
            ResponseEntity.badRequest().body(e.message ?: "Login error")
        }

    @GetMapping("/get-user")
    fun getUser(): ResponseEntity<Any> =
        try {
            ResponseEntity.ok(userService.getCurrentUser())
        } catch (e: UsernameNotFoundException) {
            ResponseEntity.badRequest().body(e.message ?: "User not found")
        }
}
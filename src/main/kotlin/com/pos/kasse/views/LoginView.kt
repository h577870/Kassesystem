package com.pos.kasse.views

import com.pos.kasse.adminviews.Admin
import com.pos.kasse.adminviews.AdminLogin
import com.pos.kasse.controllers.LoginController
import com.pos.kasse.entities.Bruker
import com.pos.kasse.services.LoginService
import com.pos.kasse.styles.Footer
import com.pos.kasse.styles.LoginStyle
import com.pos.kasse.styles.Navbar
import com.pos.kasse.utils.Logger
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import tornadofx.*

class LoginView : View() {

    private val loginController: LoginController by inject()
    private val logger = Logger()
    private val errorProp = SimpleIntegerProperty()
    var loginCode by errorProp

    override val root = borderpane {

        top {
            label("Davidsens Matkolonial") {
                addClass(Navbar.wrapper)
            }
        }
        center {
            form {
                addClass(LoginStyle.form)
                fieldset("Login") {
                    field("Brukernavn") {
                        textfield().bind(loginController.brukernavnProp)
                    }
                    field("Passord") {
                        passwordfield().bind(loginController.passordProp)
                    }
                    button("Logg inn") {
                        setOnAction {
                            if (loginController.checkEmptyProps()) {
                                logger.alertOnLogin("Passord eller brukernavn kan ikke være tom!")
                                return@setOnAction
                            }
                            else {
                                loginController.setProps()
                            }
                            try {
                                loginCode = loginController.performLogin()
                            } catch (e: NoSuchElementException) {
                                e.message
                            }
                            if (loginCode == 2) {
                                this@LoginView.replaceWith(MainWindow::class,
                                        transition = ViewTransition.
                                        FadeThrough(1.seconds ,Color.TRANSPARENT))
                            } else {
                                logger.alertOnLogin("Feil brukernavn eller passord")
                            }
                        }
                    }.isDefaultButton = true
                }
            }.requestFocus()
        }
        bottom {
            hbox {
                button("Admin") {
                    setOnAction {
                        this@LoginView.replaceWith(AdminLogin::class, transition = ViewTransition
                                .FadeThrough(1.seconds, Color.TRANSPARENT))
                    }
                }
                button("Varer")
                button("Kvitteringer")
                addClass(Footer.wrapper)
            }
        }
    }

    override fun onDock() {
        loginController.cleanBrukerProps()
    }

    override fun onUndock() {
        loginController.cleanFields()
    }

    init {
        title = "Login"
    }


}

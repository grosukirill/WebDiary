import React from "react";
import {Avatar, Button, Grid, Paper, Typography} from "@material-ui/core";
import {green} from "@material-ui/core/colors";
import {withStyles} from "@material-ui/styles";
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import TextField from "@material-ui/core/TextField";
import connect from "react-redux/lib/connect/connect";
import {loginStart} from "../../store/actions/authActions";
import {Link, Redirect} from "react-router-dom";

const styles = (theme) => ({
    paper: {
        padding: 20,
        height: '70vh',
        width: 400,
        margin: '20px auto'
    },
    green: {
        color: theme.palette.getContrastText(green[500]),
        backgroundColor: green[500],
    },
    btn: {
        margin: '10% auto',
        maxWidth: '60%'
    }
})

class Login extends React.Component {

    state = {
        email: '',
        password: ''
    }

    validateInput = () => {
        const {email, password} = this.state;
        const errors = [];
        if (!email) {
            errors.push({
                field: 'email',
                code: 'EMAIL_EMPTY',
                description: 'Email required!'
            })
        }
        if (!password) {
            errors.push({
                field: 'password',
                code: 'PASSWORD_EMPTY',
                description: 'Password required!'
            })
        }
        if (errors.length > 0) {
            this.setState({
                errors: errors
            })
            return false;
        }
        return true;
    }

    handleLogin = () => {
        const {email, password} = this.state;
        if (this.validateInput()) {
            this.props.loginStart(email, password)
        }
    }

    changeValue(field, e) {
        this.setState({
            [field]: e.target.value,
        });
    }

    render() {
        const {auth} = this.props;
        if (auth.userId) {
            return (
                <Redirect to="/"/>
            )
        }
        return (
            <Grid>
                <Paper className={this.props.classes.paper}>
                    <Grid align="center">
                        <Avatar className={this.props.classes.green}>
                            <LockOutlinedIcon/>
                        </Avatar>
                        <Typography component="h1" variant="h5">
                            Войти
                        </Typography>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="email"
                            label="Email"
                            name="email"
                            autoComplete="email"
                            autoFocus
                            onChange={(e) => this.changeValue('email', e)}
                        />
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Пароль"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            onChange={(e) => this.changeValue('password', e)}
                        />
                        <Button
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={this.props.classes.btn}
                            onClick={this.handleLogin}
                        >
                            Войти
                        </Button>
                    </Grid>
                    <Grid>
                        <Grid item xs={8}>
                            <Link to='/register'>
                                {"Нет аккаунта? Зарегистрироваться"}
                            </Link>
                        </Grid>
                    </Grid>
                </Paper>
            </Grid>
        );
    }
}

const mapStateToProps = state => ({
    auth: state.authReducer,
});

export default connect(mapStateToProps, {
    loginStart
})(withStyles(styles)(Login));

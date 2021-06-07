import React from "react";
import {Avatar, Button, Grid, Paper, Typography, Link} from "@material-ui/core";
import {green} from "@material-ui/core/colors";
import {withStyles} from "@material-ui/styles";
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import TextField from "@material-ui/core/TextField";

const styles = (theme) => ({
    paper: {
        padding: 20,
        height: '70vh',
        width: 280,
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

    handleLogin = (e) => {
        console.log(this.state)
    }

    changeValue(field, e) {
        this.setState({
            [field]: e.target.value,
        });
    }

    render() {
        return (
            <Grid>
                <Paper className={this.props.classes.paper}>
                    <Grid align="center">
                        <Avatar className={this.props.classes.green}>
                            <LockOutlinedIcon/>
                        </Avatar>
                        <Typography component="h1" variant="h5">
                            Sign in
                        </Typography>
                        <TextField
                            variant="outlined"
                            margin="normal"
                            required
                            fullWidth
                            id="email"
                            label="Email Address"
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
                            label="Password"
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
                            Sign In
                        </Button>
                    </Grid>
                    <Grid>
                        <Grid item xs={8}>
                            <Link href="/signUp">
                                {"Don't have an account? Sign Up"}
                            </Link>
                        </Grid>
                    </Grid>
                </Paper>
            </Grid>
        );
    }
}

export default withStyles(styles)(Login);

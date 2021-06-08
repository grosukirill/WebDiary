import React from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import connect from "react-redux/lib/connect/connect";
import {logout} from "../../store/actions/authActions";
import {withStyles} from "@material-ui/core";
import {Redirect} from "react-router-dom";

const styles = () => ({
    title: {
        flexGrow: 1,
    },
    loginLogoutBtn: {
        position: 'absolute',
        right: '3%'
    },
    mainBtn: {
        textDecoration: 'none',
        textTransform: 'none',
        color: 'white',
        '&:hover': {
            background: 'none'
        }
    }
});

class Navbar extends React.Component {
    handleLogout = () => {
        this.props.logout()
    }

    render() {
        const {auth} = this.props;
        console.log(auth)
        let isAuthenticated = false
        if (auth.userId) {
            isAuthenticated = true
        }
        return (
            <AppBar position="static">
                <Toolbar>
                    <Button variant="raised" href="/" className={this.props.classes.mainBtn}>
                        <Typography variant="h6">
                            Diary
                        </Typography>
                    </Button>
                    {isAuthenticated &&
                    <Button className={this.props.classes.loginLogoutBtn} color="inherit" onClick={this.handleLogout}>Logout</Button>}
                    {!isAuthenticated &&
                    <Button className={this.props.classes.loginLogoutBtn} color="inherit" href="/login">Login</Button>}
                </Toolbar>
            </AppBar>
        );
    }
}

const mapStateToProps = state => ({
    auth: state.authReducer,
});

export default connect(mapStateToProps, {
    logout
})(withStyles(styles)(Navbar));


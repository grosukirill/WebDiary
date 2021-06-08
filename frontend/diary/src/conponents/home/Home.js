import React from "react";
import {Grid} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {green} from "@material-ui/core/colors";
import {Redirect} from "react-router";

const styles = (theme) => ({
    root: {
        flexGrow: 1
    },
});

class Home extends React.Component {
    render() {
        if (!localStorage.getItem('userId')) {
            return (
                <Redirect to="/login"/>
            )
        }
        return (
            <Grid className={this.props.classes.root}>
                Home
            </Grid>
        );
    }
}


export default withStyles(styles)(Home);

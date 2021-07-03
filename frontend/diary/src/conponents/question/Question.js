import React from "react";
import Grid from "@material-ui/core/Grid";
import connect from "react-redux/lib/connect/connect";
import {getQuestion} from "../../store/actions/questionActions";
import withStyles from "@material-ui/core/styles/withStyles";

const styles = (theme) => {

}

class Question extends React.Component {

    componentWillUnmount() {
        this.props.question.question = null;
    }

    render() {
        const {question} = this.props;
        const questionObject = question.question;
        return (
            <Grid>
                { questionObject ? (
                    <Grid>{questionObject.question}</Grid>
                ) : (
                    <Grid/>
                )}
            </Grid>
        );
    }
}

const mapStateToProps = state => ({
    question: state.questionReducer,
});

export default connect(mapStateToProps, {
    getQuestion
})(withStyles(styles)(Question));

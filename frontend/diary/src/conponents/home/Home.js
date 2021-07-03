import React from "react";
import {Card, CardContent, Grid, Paper, Typography} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";
import {Redirect} from "react-router";
import connect from "react-redux/lib/connect/connect";
import {getAllQuestions, getQuestion} from "../../store/actions/questionActions";
import {Link} from "react-router-dom";

const styles = (theme) => ({
    root: {
        flexGrow: 1
    },
    paper: {
        padding: 20,
        height: '85%',
        width: '90%',
        margin: '30px auto',
        boxShadow: '1px 1px 1px 1px lightgrey'
    },
    questions: {
        margin: '10px auto',
        cursor: 'pointer',
        boxShadow: '0 0 7px lightgreen'
    },
    question: {
        maxWidth: '80%',
        fontFamily: "Viaoda Libre, serif;"
    },
    cardContent: {
        margin: '10px auto',
        display: 'flex',
        flexDirection: 'row',
        justifyContent: 'space-between',
    },
    questionInfo: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        fontFamily: "'Viaoda Libre', cursive;"
    }
});

class Home extends React.Component {
    componentDidMount() {
        this.props.getAllQuestions();
    }

    handleQuestionClick = (id) => {
        this.props.getQuestion(id);
    }

    render() {
        const data = this.props.question.questions;
        if (!localStorage.getItem('userId')) {
            return (
                <Redirect to="/login"/>
            )
        }
        return (
            <div>
                {data !== null ? (
                    <Paper variant="outlined" elevation={5} className={this.props.classes.paper}>
                        <Grid className={this.props.classes.root}>
                            {data.map(item => {
                                return <Link to={"/question/" + item.id} style={{textDecoration: 'none'}}> <Card
                                    variant="outlined" className={this.props.classes.questions}
                                    onClick={() => this.handleQuestionClick(item.id)}>
                                    <CardContent className={this.props.classes.cardContent}>
                                        <Typography variant="h4" className={this.props.classes.question}>
                                            {item.question}
                                        </Typography>
                                        <Grid className={this.props.classes.questionInfo}>
                                            <Typography>
                                                {item.creator.firstName + " " + item.creator.lastName}
                                            </Typography>
                                            <Typography>
                                                {item.creationDate}
                                            </Typography>
                                        </Grid>
                                    </CardContent>
                                </Card></Link>
                            })}

                        </Grid>
                    </Paper>
                ) : (
                    <Grid></Grid>
                )}</div>
        );
    }
}

const mapStateToProps = state => ({
    question: state.questionReducer,
});

export default connect(mapStateToProps, {
    getAllQuestions,
    getQuestion
})(withStyles(styles)(Home));

import React from "react";
import Grid from "@material-ui/core/Grid";
import connect from "react-redux/lib/connect/connect";
import {createAnswer, getQuestion} from "../../store/actions/questionActions";
import withStyles from "@material-ui/core/styles/withStyles";
import Paper from "@material-ui/core/Paper";
import TextField from "@material-ui/core/TextField";
import CalendarTodayOutlinedIcon from '@material-ui/icons/CalendarTodayOutlined';
import ChatBubbleOutlineOutlinedIcon from '@material-ui/icons/ChatBubbleOutlineOutlined';
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import {toast} from "material-react-toastify";

const styles = (theme) => ({
    title: {
        fontSize: 30,
        textAlign: 'center',
        fontFamily: 'Viaoda Libre, serif;',
    },
    paper: {
        padding: 20,
        height: '85%',
        width: '90%',
        margin: '30px auto',
        boxShadow: '1px 1px 1px 1px lightgrey',
        display: 'grid'
    },
    newAnswerTF: {
        margin: '10% 10% 0 10%'
    },
    previousAnswersData: {
        display: 'flex',
        marginLeft: '10%',
    },
    btn: {
        margin: '10% auto',
        maxWidth: '60%'
    }
})

class Question extends React.Component {

    state = {
        answer: ""
    }

    componentWillUnmount() {
        this.props.question.question = null;
    }

    handleAnswerChange = (e) => {
        this.setState({
            answer: e.target.value
        });
    }

    handleAnswerSubmission = (questionId) => {
        if (this.state.answer === "") {
            toast.warning("Для начала напишите ответ!");
            return;
        }
        let answer = this.state.answer;
        this.setState({answer:""})
        this.props.createAnswer(answer, questionId)

    }

    render() {
        const {question} = this.props;
        const questionObject = question.question;
        const answers = this.props.question.answers;
        return (

            <Grid>
                {questionObject ? (
                    <Paper variant="outlined" className={this.props.classes.paper}>
                        <Grid className={this.props.classes.title}>{questionObject.question}</Grid>
                        <Grid>
                            {answers.map(item => {
                                return <Grid style={{margin: '20px', maxWidth: '60%'}}>
                                    <Grid className={this.props.classes.previousAnswersData}>
                                        <CalendarTodayOutlinedIcon style={{color: "green", fontSize: 'xx-large'}}/>
                                        <Typography>{item.date}</Typography>
                                    </Grid>
                                    <Grid className={this.props.classes.previousAnswersData}>
                                        <ChatBubbleOutlineOutlinedIcon style={{color: "green", fontSize: 'xx-large'}}/>
                                        <Typography style={{wordBreak: 'break-word'}}>{item.answer}</Typography>
                                    </Grid>
                                </Grid>
                            })}
                        </Grid>
                        <TextField variant="outlined" multiline={true} onChange={this.handleAnswerChange}
                                   className={this.props.classes.newAnswerTF} value={this.state.answer}/>
                        <Button variant="contained" color="primary" className={this.props.classes.btn} onClick={() => this.handleAnswerSubmission(questionObject.id)}>Ответить</Button>
                    </Paper>
                ) : (
                    <Grid>Error</Grid>
                )}
            </Grid>
        );
    }
}

const mapStateToProps = state => ({
    question: state.questionReducer,
});

export default connect(mapStateToProps, {
    getQuestion,
    createAnswer
})(withStyles(styles)(Question));

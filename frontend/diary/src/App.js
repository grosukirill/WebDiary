import './App.css';
import React from "react";
import Login from "./conponents/login/login";
import {createMuiTheme} from "@material-ui/core";
import ThemeProvider from "@material-ui/styles/ThemeProvider";

const theme = createMuiTheme({
    palette: {
        primary: {
            main: '#228b22',
        },
        secondary: {
            main: '#19857b',
        },
        error: {
            main: "#FF0000",
        },
        background: {
            default: '#fff',
        },
    }
});

function App() {
    return (
        <ThemeProvider theme={theme}>
            <div className="App">
                <Login/>
            </div>
        </ThemeProvider>
    );
}

export default App;

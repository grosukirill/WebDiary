import { rootReducer } from "./reducers";
import { createStore, applyMiddleware, Store, EmptyObject, Action } from "redux";
import thunk from 'redux-thunk';

const store = createStore(rootReducer, applyMiddleware(thunk));

export type AppDispatch = typeof store.dispatch
export type RootGetState = ReturnType<typeof store.getState>

export type StoreState = Store<EmptyObject & RootState, Action<any>>

export default store;
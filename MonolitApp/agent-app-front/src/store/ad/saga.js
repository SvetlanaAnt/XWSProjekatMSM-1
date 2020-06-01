
import { take, put, call } from 'redux-saga/effects';
import { history } from '../../index';

import AdServices from '../../services/AdServices';

import {
    CREATED_AD,
    FETCH_ADS,
    FETCH_AD
} from './constants';

import {
    putAds
} from './actions';

import {
    putSuccessMsg
} from '../common/actions';


export function* createdAd(){
    const { payload } = yield take(CREATED_AD);
    const data = yield call(AdServices.createdAd, payload); 
    yield put(putSuccessMsg(data));
    history.push('/');
    
}

export function* fetchAds() {
    const { payload } = yield take(FETCH_ADS);
    yield put(putAds({ 'isFetch': false }));
    const data = yield call(AdServices.fetchAdsPaginated, payload);
    console.log(data);
    yield put(putAds({
        'data': data.ads,
        'totalPageCnt': data.totalPageCnt,
        'nextPage': payload.nextPage,
        'size': payload.size,
        'isFetch': true
    }));
}

export function* fetchAd() {
    const { payload } = yield take(FETCH_AD);
    yield put(putAds({ 'isFetch': false }));
    const data = yield call(AdServices.fetchAd, payload);
    console.log(data);
    yield put(putAds({
        'data': data.ad,
        'isFetch': true
    }));
}


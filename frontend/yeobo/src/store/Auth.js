// Access Token

// createSlice로 간단하게 redux 액션 생성자와 전체 슬라이스에 대한 reducer을 선언하여 사용 가능
import { createSlice } from "@reduxjs/toolkit";

export const TOKEN_TIME_OUT = 600 * 1000;

export const tokenSlice = createSlice({
  name: "authToken",
  initialState: {
    authenticated: false, // 현재 로그인 여부 간단 확인 위한 선언
    accessToken: null, // 액세스 토큰 저장
    expireTime: null, // 액세스 토큰 만료시간
    // email: "",
  },
  reducers: {
    // 액세스 토큰 정보 저장
    SET_TOKEN: (state, action) => {
      state.authenticated = true;
      state.accessToken = action.payload;
      state.expireTime = new Date().getTime() + TOKEN_TIME_OUT;
      // state.email = action.payload.email;
      console.log(state.accessToken);
    },
    //  값을 모두 초기화하여 액세스 토큰에 대한 정보도 삭제함
    DELETE_TOKEN: (state) => {
      state.authenticated = false;
      state.accessToken = null;
      state.expireTime = null;
      // state.email = "";
    },
  },
});

export const { SET_TOKEN, DELETE_TOKEN } = tokenSlice.actions;

export default tokenSlice.reducer;

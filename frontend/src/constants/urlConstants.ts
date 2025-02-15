
export const BASE_URL = process.env.REACT_APP_BASE_URL || "http://localhost:8080";
// export const BASE_URL = "http://172.16.1.168:8080";
export const API_BASE_URL = BASE_URL + "/api/v1";
export const WEBSOCKET_URL = BASE_URL + "/websocket";

export const USER = "/user";
export const REGISTRATION = "/registration";
export const CART = "/cart";
export const PERFUMES = "/perfumes";

// admin
export const ADMIN_ADD = "/admin/add";
export const ADMIN_EDIT = "/admin/edit";
export const ADMIN_DELETE = "/admin/delete";
export const ADMIN_USER = "/admin/user";
export const ADMIN_USER_ALL = "/admin/user/all";
export const ADMIN_ORDER = "/admin/order";
export const ADMIN_ORDERS = "/admin/orders";
export const ADMIN_GRAPHQL_USER = "/admin/graphql/user";
export const ADMIN_GRAPHQL_USER_ALL = "/admin/graphql/user/all";
export const ADMIN_GRAPHQL_ORDERS = "/admin/graphql/orders";
export const ADMIN_GRAPHQL_ORDER = "/admin/graphql/order";

// auth
export const AUTH_LOGIN = "/auth/login";
export const AUTH_FORGOT = "/auth/forgot";
export const AUTH_RESET = "/auth/reset";
export const AUTH_EDIT_PASSWORD = "/auth/edit/password";
export const REGISTRATION_ACTIVATE = "/registration/activate";

// order
export const ORDER = "/order";
export const ORDER_GRAPHQL = "/order/graphql";

// review
export const REVIEW = "/review";

// user
export const USERS = "/users";
export const USERS_CART = "/users/cart";
export const USERS_GRAPHQL = "/users/graphql";

// perfumes
export const PERFUMES_IDS = "/perfumes/ids";
export const PERFUMES_SEARCH = "/perfumes/search";
export const PERFUMES_SEARCH_TEXT = "/perfumes/search/text";
export const PERFUMES_GRAPHQL_PERFUME = "/perfumes/graphql/perfume";
export const PERFUMES_GRAPHQL_PERFUMES = "/perfumes/graphql/perfumes";
export const PERFUMES_GRAPHQL_IDS = "/perfumes/graphql/ids";

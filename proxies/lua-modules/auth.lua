local _M = {}

local function has_role(role_to_check, roles)
  for _, role in ipairs(roles) do
    if role_to_check == role then
      return true
    end
  end
  return false
end

function _M.verify_jwt(realm, role, token_name)
  local cjson = require("cjson")
  local opts = {
    discovery = "http://192.168.2.50:8080/auth/realms/" .. realm .. "/.well-known/openid-configuration",
    token_signing_alg_values_expected = { "RS256" },
    auth_accept_token_as = "cookie:" .. token_name,
    pass_cookies = token_name
  }
  local res, err = require("resty.openidc").bearer_jwt_verify(opts)
  if err then
    local timestamp = os.date("!%Y-%m-%dT%H:%M:%SZ")
    local unauthorized = {
      status = ngx.HTTP_UNAUTHORIZED,
      timestamp = timestamp,
      error = err and err or "no access_token provided"
    }
    ngx.status = ngx.HTTP_UNAUTHORIZED
    ngx.header["Content-Type"] = "application/json"
    ngx.say(cjson.encode(unauthorized))
    ngx.exit(ngx.HTTP_UNAUTHORIZED)
  end
  if has_role(role, res.realm_access.roles) then
    ngx.req.set_header("X-Role", res.sub)
    if role == "student" or role == "teacher" then
      ngx.req.set_header("X-User-Id", res.sub)
      ngx.req.set_header("X-Department-Id", res.dep_id)
      ngx.req.set_header("X-University-Id", res.uni_id)
    end
  else
    local timestamp = os.date("!%Y-%m-%dT%H:%M:%SZ")
    local forbidden = {
      status = ngx.HTTP_FORBIDDEN,
      timestamp = timestamp,
      error = "Forbidden"
    }
    ngx.status = ngx.HTTP_FORBIDDEN
    ngx.header["Content-Type"] = "application/json"
    ngx.say(cjson.encode(forbidden))
    ngx.exit(ngx.HTTP_FORBIDDEN)
  end
end

return _M

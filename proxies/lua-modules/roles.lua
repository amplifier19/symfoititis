local _M = {}

function _M.check_role(role_to_check)
  local role = ngx.req.get_headers()["X-Role"]
  if role ~= role_to_check then
    local cjson = require("cjson")
    local timestamp = os.date("!%Y-%m-%dT%H:%M:%SZ")
    local response = {
      status = ngx.HTTP_FORBIDDEN,
      timestamp = timestamp,
      error = "Forbidden"
    }
    ngx.status = ngx.HTTP_FORBIDDEN
    ngx.header["Content-Type"] = "application/json"
    ngx.say(cjson.encode(response))
    ngx.exit(ngx.HTTP_FORBIDDEN)
  end
end

return _M

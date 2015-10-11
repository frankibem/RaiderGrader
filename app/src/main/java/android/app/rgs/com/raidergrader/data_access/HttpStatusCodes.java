package android.app.rgs.com.raidergrader.data_access;

/**
 * Created by Frank Ibem on 10/10/2015.
 */
public class HttpStatusCodes {
    /**
     * The request could not be completed due to a network or other error
     */
    public static int Incomplete = -1;

    public static int Continue = 100;
    public static int SwitchingProtocols = 101;
    public static int OK = 200;
    public static int Created = 201;
    public static int Accepted = 202;
    public static int NonAuthoritativeInformation = 203;
    public static int NoContent = 204;
    public static int ResetContent = 205;
    public static int PartialContent = 206;
    public static int MultipleChoices = 300;
    public static int Ambiguous = 300;
    public static int MovedPermanently = 301;
    public static int Moved = 301;
    public static int Found = 302;
    public static int Redirect = 302;
    public static int SeeOther = 303;
    public static int RedirectMethod = 303;
    public static int NotModified = 304;
    public static int UseProxy = 305;
    public static int Unused = 306;
    public static int TemporaryRedirect = 307;
    public static int RedirectKeepVerb = 307;
    public static int BadRequest = 400;
    public static int Unauthorized = 401;
    public static int PaymentRequired = 402;
    public static int Forbidden = 403;
    public static int NotFound = 404;
    public static int MethodNotAllowed = 405;
    public static int NotAcceptable = 406;
    public static int ProxyAuthenticationRequired = 407;
    public static int RequestTimeout = 408;
    public static int Conflict = 409;
    public static int Gone = 410;
    public static int LengthRequired = 411;
    public static int PreconditionFailed = 412;
    public static int RequestEntityTooLarge = 413;
    public static int RequestUriTooLong = 414;
    public static int UnsupportedMediaType = 415;
    public static int RequestedRangeNotSatisfiable = 416;
    public static int ExpectationFailed = 417;
    public static int UpgradeRequired = 426;
    public static int InternalServerError = 500;
    public static int NotImplemented = 501;
    public static int BadGateway = 502;
    public static int ServiceUnavailable = 503;
    public static int GatewayTimeout = 504;
    public static int HttpVersionNotSupported = 505;
}
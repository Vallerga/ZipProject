(function(window){
	var post = function () {
		return function (url, payload, headers, xhrFields, callback) {
		  var xhr = new XMLHttpRequest();
		  xhr.onloadstart = function(xhr) {
			xhr.responseType = 'json';
			if (xhrFields) {
				Object.keys(xhrFields).forEach(function (k) {
				xhr[k] = xhrFields[k];
				});
			}
			xhr.xhrFields = xhrFields;
		  }

		  xhr.onreadystatechange = function () {
			if (xhr.readyState === 4) {
			  if (xhr.status > 399) {
				return callback(xhr, null);
			  }

			  return callback(null, xhr.getResponseHeader('content-type').substr(0, 'application/json'.length) === 'application/json' ? JSON.parse(xhr.response) : xhr.response);
			}
		  };

		  xhr.open('POST', url);

		  if (headers) {
			xhr.setRequestHeader('Content-Type', 'application/json');
			Object.keys(headers).forEach(function (k) {
			  xhr.setRequestHeader(k, headers[k]);
			});
		  }

		  xhr.send(JSON.stringify(payload));
		};
	  }();

	  var get = function () {
		return function (url, payload, headers, xhrFields, callback) {
		  var xhr = new XMLHttpRequest();
		  xhr.onloadstart = function(xhr) {
			xhr.responseType = 'json';
			if (xhrFields) {
				Object.keys(xhrFields).forEach(function (k) {
				xhr[k] = xhrFields[k];
				});
			}
		  }
		  xhr.onreadystatechange = function () {
			if (xhr.readyState === 4) {
			  if (xhr.status > 399) {
				return callback(xhr, null);
			  }

			  return callback(null, xhr.getResponseHeader('content-type').substr(0, 'application/json'.length) === 'application/json' ? JSON.parse(xhr.response) : xhr.response);
			}
		  };

		  var qs = payload && Object.keys(payload).map(function (k) {
			return [encodeURIComponent(k), encodeURIComponent(payload[k])].join('=');
		  }).join('&');
		  url = qs ? [url, qs].join('?') : url;
		  xhr.open('GET', url);

		  if (headers) {
			xhr.setRequestHeader('Content-Type', 'application/json');
			Object.keys(headers).forEach(function (k) {
			  xhr.setRequestHeader(k, headers[k]);
			});
		  }

		  xhr.send();
		};
	  }();

	  var del = function () {
		return function (url, payload, headers, xhrFields, callback) {
		  var xhr = new XMLHttpRequest();
		  xhr.onloadstart = function(xhr) {
			xhr.responseType = 'json';
			if (xhrFields) {
				Object.keys(xhrFields).forEach(function (k) {
				xhr[k] = xhrFields[k];
				});
			}
		  }
		  xhr.onreadystatechange = function () {
			if (xhr.readyState === 4) {
			  if (xhr.status > 399) {
				return callback(xhr, null);
			  }

			  return callback(null, xhr.getResponseHeader('content-type').substr(0, 'application/json'.length) === 'application/json' ? JSON.parse(xhr.response) : xhr.response);
			}
		  };

		  var qs = payload && Object.keys(payload).map(function (k) {
			return [encodeURIComponent(k), encodeURIComponent(payload[k])].join('=');
		  }).join('&');
		  url = qs ? [url, qs].join('?') : url;
		  xhr.open('DELETE', url);

		  if (headers) {
			xhr.setRequestHeader('Content-Type', 'application/json');
			Object.keys(headers).forEach(function (k) {
			  xhr.setRequestHeader(k, headers[k]);
			});
		  }

		  xhr.send();
		};
	  }();

	  window.client = {
		get: get,
		post: post,
		del: del
	  };
})(window);

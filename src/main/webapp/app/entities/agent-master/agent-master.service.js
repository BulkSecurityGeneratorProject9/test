(function() {
    'use strict';
    angular
        .module('testApp')
        .factory('AgentMaster', AgentMaster);

    AgentMaster.$inject = ['$resource'];

    function AgentMaster ($resource) {
        var resourceUrl =  'api/agent-masters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('EthnicGroupDetailController', EthnicGroupDetailController);

    EthnicGroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EthnicGroup'];

    function EthnicGroupDetailController($scope, $rootScope, $stateParams, previousState, entity, EthnicGroup) {
        var vm = this;

        vm.ethnicGroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:ethnicGroupUpdate', function(event, result) {
            vm.ethnicGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

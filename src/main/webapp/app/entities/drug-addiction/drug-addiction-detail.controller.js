(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DrugAddictionDetailController', DrugAddictionDetailController);

    DrugAddictionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DrugAddiction'];

    function DrugAddictionDetailController($scope, $rootScope, $stateParams, previousState, entity, DrugAddiction) {
        var vm = this;

        vm.drugAddiction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:drugAddictionUpdate', function(event, result) {
            vm.drugAddiction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

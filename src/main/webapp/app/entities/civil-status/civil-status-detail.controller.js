(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('CivilStatusDetailController', CivilStatusDetailController);

    CivilStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CivilStatus'];

    function CivilStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CivilStatus) {
        var vm = this;

        vm.civilStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:civilStatusUpdate', function(event, result) {
            vm.civilStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

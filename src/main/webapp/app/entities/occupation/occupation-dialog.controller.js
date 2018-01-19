(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('OccupationDialogController', OccupationDialogController);

    OccupationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Occupation'];

    function OccupationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Occupation) {
        var vm = this;

        vm.occupation = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.occupation.id !== null) {
                Occupation.update(vm.occupation, onSaveSuccess, onSaveError);
            } else {
                Occupation.save(vm.occupation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:occupationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

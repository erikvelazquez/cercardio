(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('BloodGroupDialogController', BloodGroupDialogController);

    BloodGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BloodGroup'];

    function BloodGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BloodGroup) {
        var vm = this;

        vm.bloodGroup = entity;
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
            if (vm.bloodGroup.id !== null) {
                BloodGroup.update(vm.bloodGroup, onSaveSuccess, onSaveError);
            } else {
                BloodGroup.save(vm.bloodGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:bloodGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
